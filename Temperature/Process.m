load('data_re.mat','data_re');
x_T = -5:0.01:50;
x = x_T.';
Nr = normalize(data_re,'range'); % 归一化处理 

% plot(x,Nr)
% title('Plot 1');
data = sum(Nr(:,1:35),2)./35; % 对前35列做预测

% plot(fittedmodel)
hold on
plot(x,data)

