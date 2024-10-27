BuildRoot: %{_topdir}/BUILDROOT/
Summary: Prints "Hello world!" on stdout
License: GNU
Name: hello-world-32
Version: 1
Release: 1
Group: Education
BuildArchitectures: x86_64

%description
Prints "Hello world!" on stdout, that's all !

%build
gcc -m32 -Wall -o helloworld-32 %{_sourcedir}/helloworld.c
# file %{_topdir}/BUILDROOT/hello-world-32-1-1.x86_64/usr/bin/helloworld-32

%install
rm -rf $RPM_BUILD_ROOT
mkdir -p $RPM_BUILD_ROOT/usr/bin
cp helloworld-32 $RPM_BUILD_ROOT/usr/bin/
file helloworld-32

%clean
rm -rf $RPM_BUILD_ROOT

%files
/usr/bin/helloworld-32
