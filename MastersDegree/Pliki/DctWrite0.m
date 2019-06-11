function P = DctWrite(Im,Im0,k)
%Im obraz do ktorego dopisuje ,mo¿e byæ kolorowy
%Im0 obraz ukrywany moze byc kolorowy ale zostanie zamieniony na czarnobialy
%algorytm bedzie dzialaæ tylko na blokach 8x8 z wzgledu na kwantyzacje
N=8;

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
    Rs2=dct2(reshape(Bl,1,8*8));
    
    %%%%%%%%%%%%%tutaj przeprowadzam operacje na zmienionym bloku
    
    p1=5*8+2;p2=4*8+3;%inaczej zamieniam elementy na pozycji (5,2) i (4,3)
    %k=20;%k to sila zagniezdzenia bitow
    is=0;%zmienna do sprawdzenia czy mozna poprawnie zapisac bit
    %
    
    %%%%%%%
    Rs3=Rs2;
       
    Bl = CheckBlock(Rs3,k);
    
    if (Bl==1)
    %%%%%%%
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
    
    
    if bit==0 && not(Rs3(p1)<Rs3(p2))
    %dla bitu 0 ustawiam ze dla na pozycji p1 powinna byc wartosc mniejsza
       c=Rs3(p1);
       Rs3(p1)=Rs3(p2);
       Rs3(p2)=c;
    else
       if bit==1 && not(Rs3(p1)>=Rs3(p2))
       c=Rs3(p1);
       Rs3(p1)=Rs3(p2);
       Rs3(p2)=c;
       end
    end
    
    %%%%%%%%%%
    if Rs3(p1)>Rs3(p2)
        roz=Rs3(p1)-Rs3(p2);
        if roz<k
           %t0=Rs2(p1);
           Rs3(p1)=Rs3(p1)+((k-roz)/2);
           Rs3(p2)=Rs3(p2)-((k-roz)/2);
        end
    else
        roz=Rs3(p2)-Rs3(p1);
        if roz<k
           %t0=Rs2(p2);
           Rs3(p2)=Rs3(p2)+((k-roz)/2);
           Rs3(p1)=Rs3(p1)-((k-roz)/2);
        end
    end
        
    %%%%%%%%%%%%%%%%%%%%%%
    Bl3=idct2(Rs3);
        
    Rs2=Rs3;
    Bl2=round(reshape(Bl3,8,8));
    ImP=setBlock(ImP,Bl2,hp,wp); 
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