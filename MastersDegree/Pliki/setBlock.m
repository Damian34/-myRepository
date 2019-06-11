function P = setBlock(Im,Im0,h,w)
N=8;%rozmiar bloku

h1=h*N;w1=w*N;%h i w numeruje od 0

s0 = size(Im);
h0=s0(1);w0=s0(2);

if h1>h0 || w1>w0
error('wysokosc lub szerokosc jest zbyt duza');
end

if size(Im,3)==3
    red = Im(:,:,1); % Red channel
    green = Im(:,:,2); % Green channel
    blue = Im(:,:,3); % Blue channel
    
    red(h*N+1:h*N+N,w*N+1:w*N+N)=Im0(:,:,1);
    green(h*N+1:h*N+N,w*N+1:w*N+N)=Im0(:,:,2);
    blue(h*N+1:h*N+N,w*N+1:w*N+N)=Im0(:,:,3);
    
    P=cat(3, red, green, blue);
else
    gray=Im(:,:,1);
    gray(h*N+1:h*N+N,w*N+1:w*N+N)=Im0(:,:,1);
    P=cat(1, gray);
end
    
end