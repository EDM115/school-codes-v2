Name:           yetris
Version:        1.1
Release:        1%{?dist}
Summary:        Simple tetris game

License:        GPL
URL:            http://gitlab.iu-vannes.org/root/RA5.07/
Source0:        %{name}.tar.gz

# BuildRequires:  gcc, make
# RPM can't find these even if they are installed
Requires:       glibc, libstdc++, libncurses5

%description
A simple tetris game but it slays

%prep
%setup -q -n game

%build
sudo apt install gcc make libncurses5-dev libstdc++6
make

%install
rm -rf %{buildroot}
mkdir -p %{buildroot}/usr/bin
cp bin/yetris %{buildroot}/usr/bin/
mkdir -p %{buildroot}/etc
cp src/config_game.ini %{buildroot}/etc/

%files
/usr/bin/yetris
%config(noreplace) /etc/config_game.ini
