function f = DctTest(image,mark,k)
%image to sciezka do obrazu do ktorego dopisuje
%mark to sciezka do znaczka ktory dopisuje
%k to stopien zagnierzdzenia
f=0;

A=imread(image);
A0=imread(mark);
M=DctWrite(A,A0,k);

%potrzebny folder FF
fileID = fopen('FF/info.txt','w');

fprintf(fileID,strcat(['dla k=',num2str(k),'\n']));
fprintf(fileID,'quality  PSNR  porownanie_bitow\n');
fprintf(fileID,'dla obrazow do ktorych dopisuje: \n');
fprintf(fileID,strcat(['---','  ',num2str(psnr(M,A)),'  ',num2str(ImCheck(M,A)),'\n']));


fprintf(fileID,'dla obrazow ktore wpisuje: \n');

for i=0:5:100
    imwrite(M,'FF/save.jpg','Quality',100-i);
    M2=imread('FF/save.jpg');
    A2=DctRead(A,M2,size(A0,2),size(A0,1));
        
    A2p=A2*255;
    A0p=rgb2gray(A0);
    
    PSNR=psnr(A2p,A0p);    
    if PSNR==99
        PSNR0='identic';
    else    
        PSNR0=num2str(PSNR);
    end
    
    
    check=num2str(ImCheck(A2p,A0p));
    fprintf(fileID,strcat([num2str(100-i),'  ',PSNR0,'  ',check,'\n']));  
    
    s = strcat(['FF/obraz-',num2str(100-i),'-k=',num2str(k),'.bmp']);
    imwrite(A2,s);
end

fclose(fileID);


end