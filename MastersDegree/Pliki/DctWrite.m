function P = DctWrite(Im,Im0,k)
%Im obraz do ktorego dopisuje ,mo¿e byæ kolorowy
%Im0 obraz ukrywany moze byc kolorowy ale zostanie zamieniony na czarnobialy
%algorytm bedzie dzialaæ tylko na blokach 8x8 z wzgledu na kwantyzacje
%k to stopien zagnierzdzenia
N=8;

T = dctmtx(8);
dct = @(block_struct) T * block_struct.data * T';
idct = @(block_struct) T' * block_struct.data * T;

%
    
Im0=im2bw(Im0);

s0=size(Im0);
h0=s0(1);w0=s0(2);
h0_2=h0*N;w0_2=w0*N;

s=size(Im);
h=s(1);w=s(2);

h2=floor(h/h0_2);w2=floor(w/w0_2);

if h2==0 || w2==0
    error('dopisywany obraz jest zbyt duzy');
end

%
km=0;
%

ImP=rgb2gray(Im);
%najpierw przechodze po zestawach
for i1=0:h2-1
for j1=0:w2-1
    %potem przechodze po 1 zestawie o liczbie blokow 
    %takiej samej jak wpisywany obraz
for i2=0:h0-1
for j2=0:w0-1
    hp=i1*h0+i2;
    wp=j1*w0+j2;
    Bl=getBlock(ImP,hp,wp);
    bit=Im0(i2+1,j2+1);%pobieram bit z obrazka dopisywanego
    
    %teraz nalezy przeprowadzic dct i zapis bitu
    Rs2=blockproc(double(Bl),[8 8],dct);
    
    %%%%%%%%%%%%%tutaj przeprowadzam operacje na zmienionym bloku
    
    p1=5*8+2;p2=4*8+3;%inaczej zamieniam elementy na pozycji (5,2) i (4,3)
    %k=40;%k to sila zagniezdzenia bitow
    is=0;%zmienna do sprawdzenia czy mozna poprawnie zapisac bit
    %
        
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
    
    if bit==0 && not(Rs2(5,2)<Rs2(4,3))
    %dla bitu 0 ustawiam ze dla na pozycji p1 powinna byc wartosc mniejsza
       c=Rs2(5,2);
       Rs2(5,2)=Rs2(4,3);
       Rs2(4,3)=c;
    else
       if bit==1 && not(Rs2(5,2)>=Rs2(4,3))
       c=Rs2(5,2);
       Rs2(5,2)=Rs2(4,3);
       Rs2(4,3)=c;
       end
    end
    
    %%%%%%%%%%
    if Rs2(5,2)>Rs2(4,3)
        if Rs2(5,2)-Rs2(4,3)<k
           t0=Rs2(5,2);
           Rs2(5,2)=Rs2(4,3)+(k/2);
           Rs2(4,3)=t0-(k/2);
        end
    else
        if Rs2(4,3)-Rs2(5,2)<k
           t0=Rs2(4,3);
           Rs2(4,3)=Rs2(5,2)+(k/2);
           Rs2(5,2)=t0-(k/2);
        end
    end
        
    %%%%%%%%%%%%%%%%%%%%%%
        
    Bl2=blockproc(Rs2,[8 8],idct); 
                
    %%%%%%%%%%%%%%%%%%%%%%
        ImP=setBlock(ImP,round(Bl2),hp,wp);
    else
        %w przypadku gdy bit nie moze byc ukryty to tylko zliczam takie miejsca
        km=km+1; 
    end
    
end
end
    %
end
end

display('ominiente bloki');
display(km);


Imp2=AddGrayToImage(floor(Im),floor(ImP));
%imshow(Imp2);

%display(Im0);
    P=Imp2;

end