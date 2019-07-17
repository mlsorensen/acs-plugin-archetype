##### CloudStack server scripts

This location can be used to stage any helper scripts that might go along with your plugin.

The packaging system would need to be modified to include these new files for installation on the system.

For example, with RPM a line would need to be added to the %install section of the provided spec file like so:

```
mkdir -p %{buildroot}/usr/share/cloudstack-foo-plugin/scripts
cp -rf server/scripts %{buildroot}/usr/share/cloudstack-foo-plugin/scripts
```

And also in "%files server" section we need to define the packaging environment's scripts for inclusion in the RPM:

```
%attr(750, cloud, cloud) /usr/share/cloudstack-foo-plugin/scripts/*
```
