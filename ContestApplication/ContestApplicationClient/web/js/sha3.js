
'use strict';

class Sha3 {

    static hash512(message, options) {
        return Sha3.op0(576, 1024, message, options);
    }

    static op0(r, c, M, options) {
        const defaults = {padding: 'sha-3', msgFormat: 'string', outFormat: 'hex'};
        const opt = Object.assign(defaults, options);

        const l = c / 2; // message digest output length in bits

        let msg = null;
        switch (opt.msgFormat) {
            default: // convert string to UTF-8 to ensure all characters fit within single byte
            case 'string':
                msg = utf8Encode(M);
                break;
            case 'hex-bytes':
                msg = hexBytesToString(M);
                break; // mostly for NIST test vectors
        }

        const state = [[], [], [], [], []];
        for (let x = 0; x < 5; x++) {
            for (let y = 0; y < 5; y++) {
                state[x][y] = new Sha3.Long(0, 0);
            }
        }

        const q = (r / 8) - msg.length % (r / 8);
        if (q == 1) {
            msg += String.fromCharCode(opt.padding == 'keccak' ? 0x81 : 0x86);
        } else {
            msg += String.fromCharCode(opt.padding == 'keccak' ? 0x01 : 0x06);
            msg += String.fromCharCode(0x00).repeat(q - 2);
            msg += String.fromCharCode(0x80);
        }

        const w = 64; // for keccak-f[1600]
        const blocksize = r / w * 8; // block size in bytes (≡ utf-8 characters)

        for (let i = 0; i < msg.length; i += blocksize) {
            for (let j = 0; j < r / w; j++) {
                const lo = (msg.charCodeAt(i + j * 8 + 0) << 0) + (msg.charCodeAt(i + j * 8 + 1) << 8)
                        + (msg.charCodeAt(i + j * 8 + 2) << 16) + (msg.charCodeAt(i + j * 8 + 3) << 24);
                const hi = (msg.charCodeAt(i + j * 8 + 4) << 0) + (msg.charCodeAt(i + j * 8 + 5) << 8)
                        + (msg.charCodeAt(i + j * 8 + 6) << 16) + (msg.charCodeAt(i + j * 8 + 7) << 24);
                const x = j % 5;
                const y = Math.floor(j / 5);
                state[x][y].lo = state[x][y].lo ^ lo;
                state[x][y].hi = state[x][y].hi ^ hi;
            }
            Sha3.op0_f(state);
        }

        let md = transpose(state).map(plane => plane.map(lane => lane.toString().match(/.{2}/g).reverse().join('')).join('')).join('').slice(0, l / 4);

        if (opt.outFormat == 'hex-b')
            md = md.match(/.{2}/g).join(' ');
        if (opt.outFormat == 'hex-w')
            md = md.match(/.{8,16}/g).join(' ');

        return md;

        /* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */

        function transpose(array) { // to iterate across y (columns) before x (rows)
            return array.map((row, r) => array.map(col => col[r]));
        }

        function utf8Encode(str) {
            try {
                return new TextEncoder().encode(str, 'utf-8').reduce((prev, curr) => prev + String.fromCharCode(curr), '');
            } catch (e) { // no TextEncoder available?
                return unescape(encodeURIComponent(str)); // monsur.hossa.in/2012/07/20/utf-8-in-javascript.html
            }
        }

        function hexBytesToString(hexStr) { // convert string of hex numbers to a string of chars (eg '616263' -> 'abc').
            const str = hexStr.replace(' ', ''); // allow space-separated groups
            return str == '' ? '' : str.match(/.{2}/g).map(byte => String.fromCharCode(parseInt(byte, 16))).join('');
        }
    }

    static op0_f(a) {

        const nRounds = 24; // number of rounds nᵣ = 12 + 2ℓ, hence 24 for Keccak-f[1600] [Keccak §1.2]

        const RC = [
            '0000000000000001', '0000000000008082', '800000000000808a',
            '8000000080008000', '000000000000808b', '0000000080000001',
            '8000000080008081', '8000000000008009', '000000000000008a',
            '0000000000000088', '0000000080008009', '000000008000000a',
            '000000008000808b', '800000000000008b', '8000000000008089',
            '8000000000008003', '8000000000008002', '8000000000000080',
            '000000000000800a', '800000008000000a', '8000000080008081',
            '8000000000008080', '0000000080000001', '8000000080008008',
        ].map(c => Sha3.Long.fromString(c));


        for (let r = 0; r < nRounds; r++) {
            // apply step mappings θ, ρ, π, χ, ι to the state 'a'

            // θ [Keccak §2.3.2]
            const C = [], D = []; // intermediate sub-states
            for (let x = 0; x < 5; x++) {
                C[x] = a[x][0].clone();
                for (let y = 1; y < 5; y++) {
                    C[x].hi = C[x].hi ^ a[x][y].hi;
                    C[x].lo = C[x].lo ^ a[x][y].lo;
                }
            }
            for (let x = 0; x < 5; x++) {
                // D[x] = C[x−1] ⊕ ROT(C[x+1], 1)
                const hi = C[(x + 4) % 5].hi ^ ROT(C[(x + 1) % 5], 1).hi;
                const lo = C[(x + 4) % 5].lo ^ ROT(C[(x + 1) % 5], 1).lo;
                D[x] = new Sha3.Long(hi, lo);
                // a[x,y] = a[x,y] ⊕ D[x]
                for (let y = 0; y < 5; y++) {
                    a[x][y].hi = a[x][y].hi ^ D[x].hi;
                    a[x][y].lo = a[x][y].lo ^ D[x].lo;
                }
            }

            // ρ + π [Keccak §2.3.4]
            let [x, y] = [1, 0];
            let current = a[x][y].clone();
            for (let t = 0; t < 24; t++) {
                const [X, Y] = [y, (2 * x + 3 * y) % 5];
                const tmp = a[X][Y].clone();
                a[X][Y] = ROT(current, ((t + 1) * (t + 2) / 2) % 64);
                current = tmp;
                [x, y] = [X, Y];
            }
            
            for (let y = 0; y < 5; y++) {
                const C = [];  // take a copy of the plane
                for (let x = 0; x < 5; x++)
                    C[x] = a[x][y].clone();
                for (let x = 0; x < 5; x++) {
                    a[x][y].hi = (C[x].hi ^ ((~C[(x + 1) % 5].hi) & C[(x + 2) % 5].hi)) >>> 0;
                    a[x][y].lo = (C[x].lo ^ ((~C[(x + 1) % 5].lo) & C[(x + 2) % 5].lo)) >>> 0;
                }
            }

            // ι [Keccak §2.3.5]
            a[0][0].hi = (a[0][0].hi ^ RC[r].hi) >>> 0;
            a[0][0].lo = (a[0][0].lo ^ RC[r].lo) >>> 0;
        }

        function ROT(a, d) {
            return a.rotl(d);
        }

        function debugNist(s) { // debug of state s in NIST format
            const d = transpose(s).map(plane => plane.join('')).join('')
                    .match(/.{2}/g).join(' ')
                    .match(/.{23,48}/g).join('\n');
            console.log(d);
        }

        function debug5x5(s) { // debug of state s in 5×5 format 64-bit words
            const d = transpose(s).map(plane => plane.join(' ')).join('\n');
            console.log(d);
        }

        function transpose(array) { // to iterate across y (columns) before x (rows)
            return array.map((row, r) => array.map(col => col[r]));
        }
    }

}


Sha3.Long = class {

    constructor(hi, lo) {
        this.hi = hi;
        this.lo = lo;
    }

    /**
     * Construct Long from string representation.
     */
    static fromString(str) {
        const [hi, lo] = str.match(/.{8}/g).map(i32 => parseInt(i32, 16));
        return new Sha3.Long(hi, lo);
    }

    /**
     * Copy 'this' Long.
     */
    clone() {
        return new Sha3.Long(this.hi, this.lo);
    }

    /**
     * Rotate left by n bits.
     */
    rotl(n) {
        if (n < 32) {
            const m = 32 - n;
            const lo = this.lo << n | this.hi >>> m;
            const hi = this.hi << n | this.lo >>> m;
            return new Sha3.Long(hi, lo);
        }
        if (n == 32) {
            return new Sha3.Long(this.lo, this.hi);
        }
        if (n > 32) {
            n -= 32;
            const m = 32 - n;
            const lo = this.hi << n | this.lo >>> m;
            const hi = this.lo << n | this.hi >>> m;
            return new Sha3.Long(hi, lo);
        }
    }

    toString() {
        const hi = ('00000000' + this.hi.toString(16)).slice(-8);
        const lo = ('00000000' + this.lo.toString(16)).slice(-8);

        return hi + lo;
    }

};


/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */

if (typeof module != 'undefined' && module.exports)
    module.exports = Sha3; // ≡ export default Sha3
