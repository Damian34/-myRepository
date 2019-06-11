function f = ResizeIm(k)
%k to stopien zagnierzdzenia



for i=0:5:100
    
    s = strcat(['F1/obraz-',num2str(100-i),'-k=',num2str(k),'.bmp']);
    
    A=imread(s);
    A2=imresize(A,2);    
    
    s2 = strcat(['F2/obraz-',num2str(100-i),'-k=',num2str(k),'.bmp']);
    
    imwrite(A2,s2);
end



end