load('data_re.mat','data_re');
x_T = -5:0.01:50;
x = x_T.';
Nr = normalize(data_re,'range'); % ��һ������ 

% plot(x,Nr)
% title('Plot 1');
data = sum(Nr(:,1:35),2)./35; % ��ǰ35����Ԥ��

% plot(fittedmodel)
hold on
plot(x,data)

