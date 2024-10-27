BuildRoot: %{_topdir}/BUILDROOT/
Summary: Prints "Hello world!" on stdout
License: GNU
Name: hello-world
Version: 1
Release: 2
Group: Education
BuildArchitectures: x86_64

%description
Prints "Hello world!" on stdout, that’s all !

%build
gcc -Wall -o helloworld %{_sourcedir}/helloworld.c

%install
rm -rf $RPM_BUILD_ROOT
mkdir -p $RPM_BUILD_ROOT/usr/bin
cp helloworld $RPM_BUILD_ROOT/usr/bin/
mkdir -p $RPM_BUILD_ROOT/usr/src
cp %{_sourcedir}/helloworld.c $RPM_BUILD_ROOT/usr/src/

%clean
rm -rf $RPM_BUILD_ROOT

%files
/usr/bin/helloworld
/usr/src/helloworld.c
