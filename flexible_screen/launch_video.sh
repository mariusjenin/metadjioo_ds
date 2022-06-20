#!/bin/bash

# only 1 argument
if [ $# != 1 ]
then
    echo 'bash launch_video.sh <video.mp4>'
    exit 1
fi

# extract the filename and the extension
fullname=$1
filename=$(basename -- "$fullname")
extension="${filename##*.}"
filename="${filename%.*}"

# verify that the video is a MP4
if [[ $extension == ".mp4" ]]
then
    echo 'Video must be a MP4'
    exit 1
fi

# Change the title metadata by creating a copy of the file
ffmpeg -i $fullname -codec copy -metadata title=" " "$filename.new.mp4" &> /dev/null

if [ $? != 0 ]
then
    echo -e "Error while modifying metadata"
fi

# override the file with the new one
mv "$filename.new.mp4" "$fullname"

# Launch the video given in fullscreen and loop it
vlc --fullscreen --loop --meta-title " " $fullname &> /dev/null

if [ $? != 0 ]
then
    echo -e "Error while launching the video"
fi

exit 0
