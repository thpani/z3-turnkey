[![GitHub Workflow Status](https://img.shields.io/github/workflow/status/tudo-aqua/z3-turnkey/License%20Check%20and%20Multi-Platform%20Test)](https://github.com/tudo-aqua/z3-turnkey/actions)
[![JavaDoc](https://javadoc.io/badge2/tools.aqua/z3-turnkey/javadoc.svg)](https://javadoc.io/doc/tools.aqua/z3-turnkey)
[![Maven Central](https://img.shields.io/maven-central/v/tools.aqua/z3-turnkey?logo=apache-maven)](https://search.maven.org/artifact/tools.aqua/z3-turnkey)
### The Z3-TurnKey Distribution

[The Z3 Theorem Prover](https://github.com/Z3Prover/z3/) is a widely used SMT solver that is written in C and C++. The
authors provide a Java API, however, it is not trivial to set up in a Java project. This project aims to solve this
issue.

#### Why?

The Z3 API is hard-coded to load its libraries from the OS's library directory (e.g., `/usr/lib`, `/usr/local/lib`,
etc. on Linux). These directories should not be writable by normal users. The expected workflow to use Z3 from Java
would therefore require installing a matching version of the Z3 native libraries as an administrator before using the
Java bindings.

Effectively, this makes the creation of Java applications that can be downloaded and run by a user impossible. It would
be preferable to have a Java artifact that
1. ships its own native libraries,
2. can use them without administrative privileges, and
3. can be obtained using [Maven](https://maven.apache.org/).

#### Usage

The artifact is intended as a drop-in replacement for the unpublished Z3 JAR. If your project works with the latter, it
should continue to work after substituting `z3-turnkey`. The artifact is published via Maven Central. To use it with
your preferred build management system, you can use a snippet from the
[Maven Central Repository Search](https://search.maven.org/artifact/tools.aqua/z3-turnkey) by selecting the correct
version.

#### How?

This project consists of two parts:
1. a Java loader, `Z3Loader`, that handles runtime unpacking and linking of the native support libraries, and
2. a build system that create a JAR from the official Z3 distributions that
    1. contains all native support libraries built by the Z3 project,
    2. replaces the hard-coded system library loader with `Z3Loader` by rewriting the Z3 source code,
    3. fixes the OS X library's search path to a relative one, and
    3. bundles all of the required files.
Also, JavaDoc and source JARs are generated for ease of use.

#### Building

The project is built using [Gradle](https://gradle.org/). In addition to Java 11 or higher, building requires Python 3,
an `install_name_tool` for OS X and a GPG signature key.

The project can be built and tested on the current platform using:
> ./gradlew assemble integrationTest

##### Python 3

Python 3 can be acquired as follows:
- Windows users can use the [official installer](https://www.python.org/downloads/windows/). When using the
  [Chocolatey package manager](https://chocolatey.org/), Python can be installed using
  > choco install python
- OS X users can use the [official installer](https://www.python.org/downloads/mac-osx/). When using the
  [Homebrew package manager](https://brew.sh/), Python can be installer using
  > brew install python
- Linux users should install the Python package provided by their distribution. Most likely, it is already present.

Python 3 is discovered by the build script using [https://github.com/xvik/gradle-use-python-plugin].

##### `install_name_tool`

An `install_name_tool` can be acquired as follows:
- Windows users will need to experiment with Cygwin/MinGW or Docker.
- OS X already ships an `install_name_tool`.
- Linux users can install a port from the [cctools-port](https://github.com/tpoechtrager/cctools-port/) project. 

The `install_name_tool` binary is discovered as follows:
1. If the project parameter `install_name_tool` is set, its value is used.
2. Else, `install_name_tool` is tried and used if it exists. This should be the case on OS X.
3. Else, `x86_64-apple-darwin-install_name_tool` is tried and used if it exists. This should be the case for Linux with
   cctools-port.
4. Else, the build fails.

Without an `install_name_tool`, a build can be created by setting the parameter to the `true` application. However, the
resulting artifact *will not work on OS X*!
> ./gradlew -Pinstall_name_tool=true assemble integrationTest

##### Signing

Normally, Gradle will enforce a GPG signature on the artifacts. By setting the project parameter `skip-signing`,
enforcement is disabled:
> ./gradlew -Pskip-signing assemble

##### Releasing

This project uses the `maven-pubish` plugin in combination with the `Gradle Nexus Publish Plugin`.
To publis and autoclose a version, run `./gradlew publishToSonatype closeAndReleaseSonatypeStagingRepository`
as one command. Due to [WIP in the Gradle Nexus Publish Plugin](https://github.com/gradle-nexus/publish-plugin/issues/19) this has to be run in conjunction for now.

#### License

Z3 is licensed under the [MIT License](https://github.com/Z3Prover/z3/blob/master/LICENSE.txt). The support files in
this project are licensed under the [ISC License](https://opensource.org/licenses/ISC).
