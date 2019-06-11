function Im0 = DctRead(Im,ImZ,w0,h0,k)
%Im obraz z orginalny ,mo¿e byæ kolorowy
%ImZ obraz po ukryciu w nim informajci

%Im0 obraz ukryty ,ma kolor czarnobialy
%algorytm bedzie dzialaæ tylko na blokach 8x8 z wzgledu na kwantyzacje
N=8;

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
    Bl2=reshape(Bl,1,8*8);
    Rs2=dct2(Bl2);
    
    %%%%%%%%%%%%%%%%%%%%%%%%%%
    p1=5*8+2;p2=4*8+3;%inaczej zamieniam elementy na pozycji (5,2) i (4,3)
    %k=20;%k to sila zagniezdzenia
    is=0;
    Rs3=Rs2;
    
    Bl = CheckBlock(Rs3,k);
    
    if (Bl==1)
        BlZ=getBlock(ImPZ,hp,wp);
        BlZ2=reshape(BlZ,1,8*8);
        RsZ2=dct2(BlZ2);
        if RsZ2(p1)<RsZ2(p2)
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
        
    if mean(double(bit))>0.5
        Im0(i2+1,j2+1)=1;
    else
        Im0(i2+1,j2+1)=0;
    end

    %%%%%%%%%%%%%%%%%%%%%
end
end

imshow(Im0);

end