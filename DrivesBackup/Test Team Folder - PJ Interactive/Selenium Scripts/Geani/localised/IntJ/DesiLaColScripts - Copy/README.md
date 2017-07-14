Command line instructions


Git global setup

git config --global user.name "Catalin Fleancu"
git config --global user.email "catalin.fleancu@brandpath.com"

Create a new repository

git clone http://e80-git.central.local/catalinf/test.git
cd test
touch README.md
git add README.md
git commit -m "add README"
git push -u origin master

Existing folder or Git repository

cd existing_folder
git init
git remote add origin http://e80-git.central.local/catalinf/test.git
git add .
git commit
git push -u origin master