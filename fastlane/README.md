fastlane documentation
----

# Installation

Make sure you have the latest version of the Xcode command line tools installed:

```sh
xcode-select --install
```

For _fastlane_ installation instructions, see [Installing _fastlane_](https://docs.fastlane.tools/#installing-fastlane)

# Available Actions

## Android

### android signing_report

```sh
[bundle exec] fastlane android signing_report
```

Get signing report

### android testenv

```sh
[bundle exec] fastlane android testenv
```

Test the env setup

### android increment_version_code

```sh
[bundle exec] fastlane android increment_version_code
```

Increment version code

### android increment_version

```sh
[bundle exec] fastlane android increment_version
```

Increment version

### android load_metadata

```sh
[bundle exec] fastlane android load_metadata
```

Download metadata from Google Play

### android test

```sh
[bundle exec] fastlane android test
```

Runs all the tests

### android build_for_screengrab

```sh
[bundle exec] fastlane android build_for_screengrab
```

Build debug and test APK for screenshots

### android screenshots

```sh
[bundle exec] fastlane android screenshots
```

Take screenshots

### android deploy

```sh
[bundle exec] fastlane android deploy
```

Deploy a new version to the Google Play

----

This README.md is auto-generated and will be re-generated every time [_fastlane_](https://fastlane.tools) is run.

More information about _fastlane_ can be found on [fastlane.tools](https://fastlane.tools).

The documentation of _fastlane_ can be found on [docs.fastlane.tools](https://docs.fastlane.tools).
