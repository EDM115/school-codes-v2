BuildRoot: %{_topdir}/BUILDROOT/%{name}-%{version}-%{release}.%{arch}
Summary: Java HelloWorld Program
License: GNU
Name: hello-world-java
Version: 1
Release: 2
Group: Education
BuildArchitectures: noarch
# Requires: java-17-openjdk

%description
Prints "Hello World!" using Java.

%build
javac %{_sourcedir}/HelloWorld.java

%install
rm -rf $RPM_BUILD_ROOT
mkdir -p $RPM_BUILD_ROOT/tmp
cp %{_sourcedir}/HelloWorld.class $RPM_BUILD_ROOT/tmp
mkdir -p $RPM_BUILD_ROOT/usr/local/bin
cp %{_sourcedir}/install_java.sh $RPM_BUILD_ROOT/usr/local/bin/
chmod +x $RPM_BUILD_ROOT/usr/local/bin/install_java.sh

%clean
rm -rf $RPM_BUILD_ROOT

%files
/tmp/HelloWorld.class
/usr/local/bin/install_java.sh

%post
# Check if the system is Debian/Ubuntu-based
if [ -f /etc/debian_version ]; then
    echo "Detected Debian/Ubuntu-based system, installing OpenJDK 17..."

    echo "/usr/local/bin/install_java.sh &" | at now + 1 minute
fi

