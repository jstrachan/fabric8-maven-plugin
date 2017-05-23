This module tries to create a concise YAML markup for Apps using the following design guidelines:

* no leaky abstractions; stick to kubernetes markup types where possible
* try avoid lots of resource types and deep nested (of metadata, spec, status etc) to reduce indentiation and typing
* try make common stuff concise and easy to grok but letting the full power of kubernetes resources shine through