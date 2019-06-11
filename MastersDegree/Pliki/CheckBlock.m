function Bl = CheckBlock(Rs,k)
%Rs3 blok wspó³czynników
%k moc wstawiania znaku

Bl=0;
p1=5*8+2;p2=4*8+3;
km=0;
is=0;

%display(Rs3);

if not(Rs(p1)<Rs(p2))
    %dla bitu 0 ustawiam ze dla na pozycji p1 powinna byc wartosc mniejsza
       c=Rs(p1);
       Rs(p1)=Rs(p2);
       Rs(p2)=c;
end

roz=Rs(p2)-Rs(p1);
%display(roz);

if (roz >= k)
    Bl=1;
else
    Rs4=Rs;
    Rs4(p2)=Rs4(p2)+((k-roz)/2);
    Rs4(p1)=Rs4(p1)-((k-roz)/2);
    Bl3=idct2(Rs4);
    if min(Bl3)<0 ||  max(Bl3)>255
        is=is+1;
    end
    %display(Bl3);
    %%%%%%%%%%%%%%%%%%%%%%%%%
    c=Rs4(p1);
    Rs4(p1)=Rs4(p2);
    Rs4(p2)=c;
    Bl3=idct2(Rs4);
    if min(Bl3)<0 ||  max(Bl3)>255
        is=is+1;
    end
Bl=1;    
if (is > 0)
    Bl=0;
end
%display(Bl);
%display(Rs4);
    
end    

end

