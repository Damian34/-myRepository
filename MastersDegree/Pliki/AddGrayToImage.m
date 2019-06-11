function Im = AddGrayToImage(Tc,Tg)
    %Tc bitmapa kolorowa
    %Tg ta sama bitmapa w odcieniu szarosci
    
    %%%%%%%
    
    s = size(Tc);
    h=s(1);
    w=s(2);
    
    red = Tc(:,:,1); % Red channel
    green = Tc(:,:,2); % Green channel
    blue = Tc(:,:,3); % Blue channel
    
    
    for c1=1:h
    for c2=1:w
       %c = [red(c1,c2),green(c1,c2),blue(c1,c2)];
       r=double(red(c1,c2));
       g=double(green(c1,c2));
       b=double(blue(c1,c2));
       %
       %y=0.2989*r+0.587*g+0.114*b;%y - iluminacja 
       cb=(-0.1687)*r-0.3313*g+0.5*b;%cb stopien niebieskoci
       cr=0.5*r-0.4187*g-0.0813*b;
       %
       %       
       r2 = round(Tg(c1,c2)+1.402*cr);
       g2 = round(Tg(c1,c2)-0.34413*cb-0.71414*cr);
       b2 = round(Tg(c1,c2)+1.772*cb);       
       %       
       
       if r2>255 r2=255; end
       if r2<0 r2=0; end
       if g2>255 g2=255; end
       if g2<0 g2=0; end
       if b2>255 b2=255; end
       if b2<0 b2=0; end
           
       red(c1,c2)=r2;
       green(c1,c2)=g2;
       blue(c1,c2)=b2;
       
       if c1==1
       if c2==1
       Im=[r,g,b,0,0;
           Tg(c1,c2),cb,cr,c1,c2
           r2,g2,b2,0,0];
           
       end
       end
       
    end
    end
      
    Im = cat(3, red, green, blue);
    %imshow(Im);
    
    
    
    %Im=rand(h,w);

end