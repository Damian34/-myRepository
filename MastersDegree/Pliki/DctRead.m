function Im0 = DctRead(Im,ImZ,w0,h0)
%Im obraz z orginalny ,mo¿e byæ kolorowy
%ImZ obraz po ukryciu w nim informajci

%Im0 obraz ukryty ,ma kolor czarnobialy
%algorytm bedzie dzialaæ tylko na blokach 8x8 z wzgledu na kwantyzacje
N=8;

T = dctmtx(8);
dct = @(block_struct) T * block_struct.data * T';
idct = @(block_struct) T' * block_struct.data * T;

%%%%%%%%%%%%%%%%%%%%%%%

h0_2=h0*N;w0_2=w0*N;

s=size(Im);
h=s(1);w=s(2);

h2=floor(h/h0_2);w2=floor(w/w0_2);

if h2==0 || w2==0
    error('odczytywany obraz jest zbyt duzy');
end

Im0=zeros(h0,w0)+1;
ImP=rgb2gray(Im);
ImPZ=rgb2gray(ImZ);

for i2=0:h0-1
for j2=0:w0-1
    %potem przechodze po 1 zestawie o liczbie blokow 
    %takiej samej jak wpisywany obraz
    
    bit=0;
    bitk=1;
    
%najpierw przechodze po zestawach
for i1=0:h2-1
for j1=0:w2-1
    hp=i1*h0+i2;
    wp=j1*w0+j2;
    
    %%%%%%%%%%najpierw sprawdzam na orginalnym czy mozna bylo tam ukryc bit
    
    Bl=getBlock(ImP,hp,wp);
    
    %teraz nalezy przeprowadzic dct i zapis bitu
     Rs2=blockproc(double(Bl),[8 8],dct);
    
    %%%%%%%%%%%%%%%%%%%%%%%%%%
    p1=5*8+2;p2=4*8+3;%inaczej zamieniam elementy na pozycji (5,2) i (4,3)
    k=20;%k to sila zagniezdzenia
    is=0;
    
    
    %%%%%%%
    Rs3=Rs2;
    if not(Rs3(5,2)<Rs3(4,3))
       c=Rs3(5,2);
       Rs3(5,2)=Rs3(4,3);
       Rs3(4,3)=c;
       
        if Rs3(5,2)-Rs3(4,3)<k
           t0=Rs3(5,2);
           Rs3(5,2)=Rs3(4,3)+(k/2);
           Rs3(4,3)=t0-(k/2);
        end
        %%%
        Bl3=blockproc(Rs3,[8 8],idct);    
        if min(min(Bl3))<0 ||  max(max(Bl3))>255
        is=is+1;
        end
        %%%
    end
    
    Rs3=Rs2;
    if not(Rs3(5,2)>=Rs3(4,3))
       c=Rs3(5,2);
       Rs3(5,2)=Rs3(4,3);
       Rs3(4,3)=c;
       
        if Rs3(4,3)-Rs3(5,2)<k
           t0=Rs3(4,3);
           Rs3(4,3)=Rs3(5,2)+(k/2);
           Rs3(5,2)=t0-(k/2);
        end
        %%%
        Bl3=blockproc(Rs3,[8 8],idct); 
        if min(min(Bl3))<0 ||  max(max(Bl3))>255
        is=is+1;
        end
        %%%
    end
    %%%%%%%
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
    if is==0
    BlZ=getBlock(ImPZ,hp,wp);
    RsZ2=blockproc(double(BlZ),[8 8],dct);    
    
    %%%
    if RsZ2(5,2)<RsZ2(4,3)
        %tzn ze zapisano 0
        bit(bitk)=0;
        bitk=bitk+1;
    else
    	%w przeciwnym wypadku 1
        bit(bitk)=1;
        bitk=bitk+1;
    end
    
    end
    
end
end

    if not(size(bit,2)==0)
        
    if mean(double(bit))>0.5
        Im0(i2+1,j2+1)=1;
    else
        Im0(i2+1,j2+1)=0;
    end

    %%%%%%%%%%%%%%%%%%%%%
    end
end
end

imshow(Im0);

end