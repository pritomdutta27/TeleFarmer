package com.theroyalsoft.telefarmer.utils;

import timber.log.Timber;

/**
 * Created by Pritom Dutta on 19/9/23.
 */
public class ModifySdp {
    public static String sdpModify(String sdp) {

        String[] splitSdp = sdp.split("\r\n");
        String sdpWithBitRate = "";


        String bitRate = ";x-google-max-bitrate=500;x-google-min-bitrate=72;x-google-start-bitrate=72";

        for (int position = 0; position < splitSdp.length; position++) {

            Timber.e("SDP_0:" + splitSdp[position]);

            if (splitSdp[position].contains("a=fmtp")) {

                splitSdp[position] = splitSdp[position] + bitRate;
                Timber.e("SDP_1:" + splitSdp[position]);

            } else if (splitSdp[position].contains("a=mid:1") || splitSdp[position].contains("a=mid:video)")) {
                splitSdp[position] = splitSdp[position] + "\r\nb=AS:500";
                Timber.e("SDP_2:" + splitSdp[position]);
            } else {
                Timber.e("SDP_3:" + splitSdp[position]);
            }

            splitSdp[position] = splitSdp[position] + "\r\n";

            sdpWithBitRate = sdpWithBitRate + splitSdp[position];
        }


        if (sdp.equals(sdpWithBitRate)) {
            Timber.e("sdpWithBitRate same");
        } else {
            Timber.e("sdpWithBitRate not same");
        }

        return sdpWithBitRate;
    }
}
