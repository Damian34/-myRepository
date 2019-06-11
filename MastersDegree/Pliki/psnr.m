function PSNR = psnr(distImg, origImg)

     origImg = double(origImg);
     distImg = double(distImg);
 
     [M N] = size(origImg);
     error = origImg - distImg;
     MSE = sum(sum(error .* error)) / (M * N);

     %mx=max(max(max(origImg)));
     if(MSE > 0)
     PSNR = 10*log(255*255/MSE) / log(10);
     %PSNR = 10*log(mx*mx/MSE) / log(10);
     else
     PSNR = 99;
     end      
     PSNR=mean(PSNR);
     
end