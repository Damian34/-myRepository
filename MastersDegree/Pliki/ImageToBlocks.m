function [B,S] = ImageToBlocks(Im,Im0)
%Im to obraz do ktorego dopisuje 
%i Im0 to obraz ktory wpisuje
%w funkcji pobieram obraz i dziele go na bloki
N=8;%wielkosc bloku

s = size(Im);
h=s(1);w=s(2);

s0 = size(Im0);
h0=s0(1);w0=s0(2);

h_2=floor(h/N);w_2=floor(w/N);
h0_2=h0*N;w0_2=w0*N;
h0_3=floor(h/h0_2);w0_3=floor(w/w0_2);

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    red = Im(:,:,1); % Red channel
    green = Im(:,:,2); % Green channel
    blue = Im(:,:,3); % Blue channel
    
blocks=zeros(h0_3,w0_3,h0,w0,N,N,3);

for i1=1:h0_3
for j1=1:w0_3
   % 
for i2=1:h0
for j2=1:w0
    %
    for i3=1:N
    for j3=1:N
    a=(i1-1)*(h0-1)*N+(i2-1)*N+(i3-1)+1;
    b=(j1-1)*(h0-1)*N+(j2-1)*N+(j3-1)+1;
    
    blocks(i1,j1,i2,j2,i3,j3,:)=[red(a,b),green(a,b),blue(a,b)];
    end
    end
    %
end
end
    %
end
end

B=blocks;%[w h ; w0 h0];
S=[h0_3,w0_3,h0,w0,N,N];
end