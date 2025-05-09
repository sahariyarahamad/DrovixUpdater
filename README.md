<p align="center">
  <img height="300px" weight=300px" alter="DrovixUpdaterLibraryLogo" src="https://raw.githubusercontent.com/sahariyarahamad/DrovixUpdater/refs/heads/main/DrovixUpdaterLogo.png">
</p>

<br>

# About Library
DrovixUpdater is a lightweight and easy-to-use library that allows you to download and install APK updates from a direct URL or your server — all handled with just a few lines of code.

<br>

# Features
- Downloads APK files from a direct link
- Automatically installs the APK after download
- FileProvider-safe installation for Android 7.0+
- Callback/Listener support for download progress, errors, and completion
- No external dependencies required
- No File parmission hassle
- Update not store/save user external storage
- Use in-app storage

<br>

# Library implementation guide

***Choose your language***
### [**Implementation for JAVA**](?filename=README.md#for-java)
### [**Implementation for KOTLIN**](?filename=README.md#for-kotlin)

<br>

------

# FOR JAVA

<br>

## Step 1. Add the JitPack repository to your build file
Add it in your root ```settings.gradle``` at the end of repositories:

<br>

```gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
  ```

<br>

## Step 2. Add the dependency
Add it in your root ```build.gradle(Module:app)``` at the end of repositories:

<br>

```gradle
implementation 'com.github.sahariyarahamad:DrovixUpdater:CURRENT_VERSION'
```
<br>

***Current version*** 

<br>

[![Current_version](https://img.shields.io/github/v/release/sahariyarahamad/DrovixUpdater)](https://github.com/sahariyarahamad/DrovixUpdater/releases)

<br>

> **⚠️Note:** Steps 3 and 4 are applicable for Android SDK version 24 and above (Android 7.0+).

<br>

## Step 3. Create a file
Create a ```path_provider``` name file into ```app > res > xml > path_provider``` and past below code

<br>

```xml
<?xml version="1.0" encoding="utf-8"?>
<paths xmlns:android="http://schemas.android.com/apk/res/android">
    <cache-path
        name="cache"
        path="."
        />
</paths>
```

<br>

## Step 4. AndroidManifest.xml Configuration
Paste the following code inside the ```<application>``` tag of your ```AndroidManifest.xml``` file located at ```app > manifests > AndroidManifest.xml.```

<br>

```xml
<provider
    android:authorities="${applicationId}.provider"
    android:name="androidx.core.content.FileProvider"
    android:exported="false"
    android:grantUriPermissions="true">
    <meta-data
        android:name="android.support.FILE_PROVIDER_PATHS"
        android:resource="@xml/path_provider"
    />
</provider>
```

<br>

## Step 5. Write permission on AndroidManifest.xml

```xml
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES"/>
```

<br>

## Step 6. Add below code
Add below code in your specific activity for download and install updated version

<br>

```java
/**
 * NOTE: The URL must end with ".apk" to ensure proper APK file handling.
 */

new DrovixUpdater()
        .setUp(MainActivity.this)
        .setUrl(apkUrl)
        .setFileNameFromUrl()
        .setOnUpdateDownloadListener(new OnUpdateDownloadListener() {
            @Override
            public void OnConnectingBuffer(String connectingMsg) {
                // handle when connecting
            }

            @Override
            public void OnDownloadProgress(int progress) {
                // handle progress
            }

            @Override
            public void OnDownloadComplete(File path) {
                // handle when download complete
            }

            @Override
            public void OnDownloadError(String errorMsg) {
                // handle when download error
            }
        })
        .start();
```

<br>

------

<br>

***See details about [methods](?filename=README.md#about-methods)***

<br>

<br>

------

# FOR KOTLIN

<br>

## Step 1. Add the JitPack repository to your build file
Add it in your ```settings.gradle.kts``` at the end of repositories:

<br>

```gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```
<br>

## Step 2. Add the dependency

<br>

```gradle
implementation("com.github.sahariyarahamad:DrovixUpdater:CURRENT_VERSION")
```
<br>

***Current version*** 

<br>

[![Current_version](https://img.shields.io/github/v/release/sahariyarahamad/DrovixUpdater)](https://github.com/sahariyarahamad/DrovixUpdater/releases)

<br>

> **⚠️Note:** Steps 3 and 4 are applicable for Android SDK version 24 and above (Android 7.0+).

<br>

## Step 3. Create a file
Create a ```path_provider``` name file into ```app > res > xml > path_provider``` and past below code

<br>

```xml
<?xml version="1.0" encoding="utf-8"?>
<paths xmlns:android="http://schemas.android.com/apk/res/android">
    <cache-path
        name="cache"
        path="."
        />
</paths>
```

<br>

## Step 4. AndroidManifest.xml Configuration
Paste the following code inside the ```<application>``` tag of your ```AndroidManifest.xml``` file located at ```app > manifests > AndroidManifest.xml.```

<br>

```xml
<provider
    android:authorities="${applicationId}.provider"
    android:name="androidx.core.content.FileProvider"
    android:exported="false"
    android:grantUriPermissions="true">
    <meta-data
        android:name="android.support.FILE_PROVIDER_PATHS"
        android:resource="@xml/path_provider"
    />
</provider>
```
<br>

## Step 5. Write permission on AndroidManifest.xml

```xml
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES"/>
```

<br>

## Step 6. Add below code
Add below code in your specific Activity for download and install updated apk

<br>

```kotlin
/**
 * NOTE: The URL must end with ".apk" to ensure proper APK file handling.
 */
DrovixUpdater()
    .setUp(this@MainActivity)
    .setUrl(apkUrl)
    .setFileNameFromUrl()
    .setOnUpdateDownloadListener(object : OnUpdateDownloadListener {
        override fun OnConnectingBuffer(connectingMsg: String) {
            // handle when connecting
        }

        override fun OnDownloadProgress(progress: Int) {
            // handle progress
        }

        override fun OnDownloadComplete(path: File) {
            // handle when download is complete
        }

        override fun OnDownloadError(errorMsg: String) {
            // handle when download error
        }
    })
    .start()

```

<br>

------

<br>

***See details about [methods](?filename=README.md#about-methods)***

<br>

------

<p align="center">
END IMPLEMENTATION
</p>

------

<br>

# About methods 

<br>

| Methods | Use case |
|---------|----------|
| setUp() | Initializes the updater with the current activity or app context.  Required as the base to access in-app directory, start installation, must be the first method called. |
| setUrl()| This is the core of the update logic. Without the URL, no file can be downloaded. <br> **Note: The URL must end with .apk.** |
| setFileName() | Manually sets the name of the downloaded file. If you want to control the filename instead of generating it automatically. |
| setFileNameFromUrl() | Automatically extracts the filename from the URL. Saves you from manually writing the filename. It guesses based on the .apk in the URL. |
| setOnUpdateDownloadListener() | Set a listener to get update download callbacks. To show progress, status messages, handle errors, and notify when download is complete. |
| OnConnectingBuffer() | Notifies when the connection starts. |
| OnDownloadProgress() | Notifies download progress in %. |
| OnDownloadComplete() | Called when APK download finishes. When download will complete then you get return file location where your update apk downloaded, if you want, you will delete this file after updated version isntall. |
| OnDownloadError() | Called when any error happens. |
| start() | Starts downloading and installing the update. This triggers the process. Without calling this, the update won’t start. |

<br>

# LICENSE

Copyright (c) 2025 - Present by [Sahariyar Ahamad]

All rights reserved.

This software is licensed, not sold. You are allowed to use this software for personal or commercial purposes **without modification**.

You are **not allowed** to:
- Modify, decompile, reverse-engineer, or disassemble any part of this software.
- Redistribute, sublicense, or resell it.
- Claim authorship of the software or any part of it.

Unauthorized use or modification is strictly prohibited.

Feel free to use this library!
If it helps you or you find it useful, please consider hitting the Star button in the top right corner, or share it with your friends and fellow developers.
Thank you for choosing this library — with love from the author [Sahariyar Ahamad](https://github.com/sahariyarahamad/)

<br>

# Author

This Software/Library made & maintaining by [Sahariyar Ahamad](https://github.com/sahariyarahamad/)


