call javac JavaDeleteFolder.java
cd ginkgo-seed
call mvn clean 
echo 'Start creating archetype.'
call mvn archetype:create-from-project -Darchetype.properties=../archetype.properties

cp ../archetype-metadata.xml  target/generated-sources/archetype/src/main/resources/META-INF/maven/archetype-metadata.xml
set resources="target/generated-sources/archetype/src/main/resources/archetype-resources/"

rm -rf %resources%/.idea/
rm %resources%/ginkgo-seed.iml
rm %resources%/application-api/application-api.iml
rm %resources%/application-impl/application-impl.iml
rm %resources%/rest/rest.iml
rm %resources%/spa/spa.iml
cd ..
java  JavaDeleteFolder ginkgo-seed/%resources%/spa/app/bower_components
java  JavaDeleteFolder ginkgo-seed/%resources%/spa/node_modules
java  JavaDeleteFolder ginkgo-seed/%resources%/spa/dist

cd ginkgo-seed/target/generated-sources/archetype
call mvn install
