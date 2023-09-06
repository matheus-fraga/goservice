const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]')
const tooltipList = [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl))

// CÓDIGO FIREBASE:

      const firebaseConfig = {
    apiKey: "AIzaSyCWarBE1EW7jCwghNCQIw47J_VNHzfAtVo",
    authDomain: "goservice-squad1.firebaseapp.com",
    projectId: "goservice-squad1",
    storageBucket: "goservice-squad1.appspot.com",
    messagingSenderId: "1067323931090",
    appId: "1:1067323931090:web:b4ccf41d68f305cdd8f00b",
      };

      const app = firebase.initializeApp(firebaseConfig);

      const storage = firebase.storage();

      let uploadedImageUrl;


      const inp = document.querySelector(".inp");
      const progressbar = document.querySelector(".progress");
      const img = document.querySelector(".img");
      const body = document.querySelector("body");
      const metaData = document.querySelector(".metaData");
      const images = document.querySelector(".images");
      const loading = document.querySelector(".loading");
      const imageUpload = document.querySelector(".imageUpload");
      const completeMsg = document.querySelector(".completeMsg");
      let file;
      let files;
      let fileName;
      let progress;
      let uploadedFileName;
      const selectImage = () => {
        inp.click();
      };
      const getImageData = (e) => {
        files = e.target.files;
        for (let i = 0; i < files.length; i++) {
            let imageData =  document.createElement("span");
            imageData.className ='filedata';
            imageData.style.display ='block';
            imageData.innerHTML = files[i].name;
            metaData.appendChild(imageData);
        }
      };

      const uploadImage = async () => {
        for (let i = 0; i < files.length; i++) {
            let url = await uploadProcess(files[i],Math.round(Math.random()*9999) +files[i].name);
            if(url){
                loading.style.display='none';
                let image = document.createElement("img");
                image.style.display='block';
                image.setAttribute('src',url);
                image.className ='img';
                images.appendChild(image);
            }
            if(i===files.length-1){
                completeMsg.innerHTML = `${files.length} files uploaded successfully`;
            }
        }
      };

      const uploadProcess = (file, fileName) => {
        return new Promise((resolve, reject) => {
          const storageRef = storage.ref().child("myimages");
          const folderRef = storageRef.child(fileName);
          const uploadtask = folderRef.put(file);
          uploadtask.on(
            "state_changed",
            (snapshot) => {
                loading.style.display='block';
              progress =
                (snapshot.bytesTransferred / snapshot.totalBytes) * 100;
              progress = Math.round(progress);
              progressbar.style.width = progress + "%";
              progressbar.innerHTML = progress + "%";
              uploadedFileName = snapshot.ref.name;
            },
            (error) => {
              reject(error);
            },
            () => {
              storage
                .ref("myimages")
                .child(uploadedFileName)
                .getDownloadURL()
                .then((url) => {

                  uploadedImageUrl = url;
                  document.getElementById("urlFoto").value = url;
                  resolve(url);
                });
            }
          );
        });
      };

