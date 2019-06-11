function P = ImCheck(A,B)
%porownoje obraz A i B
%A i B powinny mieæ te same wymiary

if size(A,3)==3
A0=rgb2gray(A);
else
A0=A;
end

if size(B,3)==3
B0=rgb2gray(B);
else
B0=B;
end

s=size(A);

c=0;
for i=1:s(1)
for j=1:s(2)
if A0(i,j)==B0(i,j)
  c=c+1;
end
end    
end
P=c/(s(1)*s(2));

end