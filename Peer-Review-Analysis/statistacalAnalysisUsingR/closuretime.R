#line graph for closure time
data <-read.csv("/home/poulami/Dropbox/Poulami-project/to-share/results/data-v04.csv")
sink()
jpeg("closuretime.jpeg")
x<-c(seq(50))
y1 <- data$noncumul.avgclosuretime.perreview
y2<-data$no.of.reviews.created
par(mar = c(5,5,2,5))
with(data, plot(x, y1, type="o", col="red4",xlab="time interval", ylab="av closure time"));
par(new= T)
with(data,plot(x, y2, axes=F,pch=20, xlab="time interval", ylab=NA, cex=1.2))
reg<-lm(y2~x)
abline(reg)
mtext(side = 4, line = 3, 'no of reviews created')
axis(side = 4)
legend("top",legend=c(expression("av closure time","no of reviews created")),lty=c(1,0),pch=c(1,20),col=c("red4","black"))

dev.off()
