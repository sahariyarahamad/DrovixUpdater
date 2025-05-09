/*
 * Copyright (c) 2025-Present by Sahariyar Ahamad
 *
 * This file is part of DrovixUpdater Library and is provided for use without modification.
 * Redistribution or modification is strictly prohibited.
 * Contact: https://github.com/sahariyarahamad/
 */

package com.sahariyar.drovixupdater.utiles;


import com.sahariyar.drovixupdater.DrovixUpdater;

import java.io.File;


public interface OnUpdateDownloadListener {
    void OnConnectingBuffer(String connectingMsg);

    void OnDownloadProgress(int progress);

    void OnDownloadComplete(File path);

    void OnDownloadError(String errorMsg);

}


