#!/bin/sh

#set -x
FEP_HOSTS="fep-ahtv-l1a.virgilio.net fep-ahtv-l1b.virgilio.net fep-ahtv-l2a.virgilio.net fep-ahtv-l2b.virgilio.net"
FEP_LOG_DIR=/store2/logs/apache-tomcat_alicehometv/logs
KPI_DESTINATION_DIR=/store4/kpi/Projects/ahtv/staging

TIMESTAMP=`date +%Y-%m-%d`
YESTERDAY_TIMESTAMP=`date +%Y%m%d --date='1 day ago'`

BASE_DIR=/home/ahtv07/logGetter
WORKING_DIR=$BASE_DIR/bin/tmp
DESTINATION_DIR=$BASE_DIR/data
ARCHIVE_DIR=$BASE_DIR/archive


rm $WORKING_DIR/*
echo "$TIMESTAMP BEGIN: " `date`

for eachFepHost in $FEP_HOSTS
do
	echo "$TIMESTAMP --- "$eachFepHost
	yesterdayLogToDownload=out-$eachFepHost.txt
	logToDownloadWhenNotRolled=out-notRolled-$eachFepHost.txt
	ssh -n httpd@$eachFepHost "find $FEP_LOG_DIR/bi.log.$YESTERDAY_TIMESTAMP |xargs ls" > $yesterdayLogToDownload &
	ssh -n httpd@$eachFepHost "find $FEP_LOG_DIR/bi.log -mtime -1 |xargs ls" > $logToDownloadWhenNotRolled &
	mypid=$!
	sleep 10
	if test -s $yesterdayLogToDownload
	then
		toBeDownloaded=`cat $yesterdayLogToDownload`
		destinationFile=$eachFepHost-`basename $toBeDownloaded`
		scp httpd@$eachFepHost:${toBeDownloaded} $WORKING_DIR/${destinationFile}
	else
		if test -s $logToDownloadWhenNotRolled
		then
			echo "$TIMESTAMP --- Rolled log bi.log.$YESTERDAY_TIMESTAMP not found. Will try to retrieve not rolled log bi.log for $eachFepHost"
			toBeDownloaded=`cat $logToDownloadWhenNotRolled`
			destinationFile=$eachFepHost-`basename $toBeDownloaded`
			scp httpd@$eachFepHost:${toBeDownloaded} $WORKING_DIR/${destinationFile}
		else
			echo `date` "WARNING: please check that FEP $eachFepHost is reachable and contains yesterday's log for KPI in folder $FEP_LOG_DIR" > errorMessage.txt 
			cat errorMessage.txt
			mailx -s "[Alice Home TV] Log for KPI from FEP $eachFepHost not sent" toctech@vtin.it < errorMessage.txt 
		fi
	fi

	rm $yesterdayLogToDownload
	rm $logToDownloadWhenNotRolled

done

cat `find $WORKING_DIR/*bi.log*` > $DESTINATION_DIR/searchahtv_$YESTERDAY_TIMESTAMP.txt
cp $DESTINATION_DIR/searchahtv_$YESTERDAY_TIMESTAMP.txt $KPI_DESTINATION_DIR

mkdir $ARCHIVE_DIR/$TIMESTAMP
mv $WORKING_DIR/*bi.log* $ARCHIVE_DIR/$TIMESTAMP

echo "$TIMESTAMP END: " `date`
