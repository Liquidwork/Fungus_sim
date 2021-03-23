% load('data_re.mat','data_re');
dataPre = sum(Nr(:,35:44),2)./9;
y = dataPre.';
x = -5:0.01:50;
coeffvalues(fittedmodel)
plot(fittedmodel)
hold on
plot(x,y)
y_pre = fittedmodel(x);
rmse = sqrt(mean((y_pre.'-y).^2))