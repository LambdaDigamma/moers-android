# Mein Moers for Android

<p align="left">
<a href="https://moers.app">
    <img src="https://img.shields.io/badge/moers.app-yellow.svg">
</a>
<a href="https://apps.apple.com/us/app/24doors/id1580211646">
    <img src="https://img.shields.io/badge/download-iOS-red.svg">
</a>
<a href="https://play.google.com/store/apps/details?id=com.lambdadigamma.moers">
    <img src="https://img.shields.io/badge/download-Android-green.svg">
</a>
</p>

The official [Mein Moers](https://moers.app) app for iOS.

> Du möchtest lieber eine Beschreibung auf Deutsch lesen?
> Dann geht's hier entlang!

## Run locally during development

### Prerequisits

Make sure that you have installed:

- Android Dolphin

Clone the repository from GitHub:

```bash
git clone https://github.com/LambdaDigamma/moers-ios.git
```

### API Keys

#### Tankerkoenig

Register for an api token on [tankerkoenig.de](https://creativecommons.tankerkoenig.de) and put it into the `local.properties` file.

```env
TANKERKOENIG_API_KEY=
```

> **Note**
>
> We are working on a development scheme that uses only static data services so that we can achieve better test coverage and generate marketing material.


## Features

♻️  **Access waste schedule** with notifications <br>
⛽️  **Compare fuel prices** to other fuel stations and quickly navigate there <br>
🅿️  **Find parking areas** and keep track of your parking timer<br>
📰  **Read news** from the local news provider<br>
📻  **Access information** about the local radio station<br>
🎤  **Find interesting events** in your area<br>

## Issues

If you find any problems or want to track issues, please use the issues feature or start a discussion in the Discussions tab.

## Deployment

Most of the tedious bookkeeping and deployment work is automated via [fastlane](https://fastlane.tools).<br>
A list of the available fastlane lanes can be found [here](fastlane/README.md).

### Technologies

We aim to follow the [Android Architecture Guide](https://developer.android.com/topic/architecture) and try to use:

- Kotlin Flow
- Kotlin Coroutines
- Jetpack Compose
- Hilt (for dependency injection)
- Repository pattern
