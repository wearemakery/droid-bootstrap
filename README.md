# droid-bootstrap

## Usage
To reference bootstrap from any project follow these steps:

1. Add bootstrap to your projects ```settings.gradle```
  ```gradle
  include 'bootstrap', ':{yourAppModule}'
  project(':bootstrap').projectDir = new File(bootstrapLocation)
  ```

2. Create a new ```gradle.properties``` file with the path to bootstraps location
  ```
  bootstrapLocation={pathOnYourFileSystem}/bootstrap/core
  ```
