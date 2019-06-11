function P = getBlock(Im,h,w)
N=8;%rozmiar bloku

h1=h*N;w1=w*N;%h i w numeruje od 0

s0 = size(Im);
h0=s0(1);w0=s0(2);

if h1>h0 || w1>w0
error('wysokosc lub szerokosc jest zbyt duza');
end

if size(Im,3)==3
red=Im(h*N+1:h*N+N,w*N+1:w*N+N,1);
green=Im(h*N+1:h*N+N,w*N+1:w*N+N,2);
blue=Im(h*N+1:h*N+N,w*N+1:w*N+N,3);

P = cat(3, red, green, blue);
else
    gray=Im(h*N+1:h*N+N,w*N+1:w*N+N,1);
    P = cat(1, gray);
end
%display('maksymalna liczba blokow w szerokosci: ');
%display(floor(w0/N))
%display('maksymalna liczba blokow w wysokosci: ');
%display(floor(h0/N))
%P=0;
end