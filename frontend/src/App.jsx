import Register from "./components/Register";
import Login from "./components/Login";
import UploadAudio from "./components/UploadAudio";

function App() {
  const token = localStorage.getItem("token");

  if (token) {
    return <UploadAudio />;
  }

  return (
    <>
      <Register />
      <hr />
      <Login />
    </>
  );
}

export default App;