const token = new URLSearchParams(window.location.search).get("token");

async function resetPassword() {
  const password = document.getElementById("password").value;
  const confirm = document.getElementById("confirm").value;
  const message = document.getElementById("message");
  const error = document.getElementById("error");
  message.textContent = "";
  error.textContent = "";

  if (!password || !confirm) {
    error.textContent = "Todos los campos son obligatorios";
    return;
  }

  if (password !== confirm) {
    error.textContent = "Las contraseñas no coinciden";
    return;
  }

  try {
    const res = await fetch("http://localhost:3000/api/auth/resetear", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ token, nuevaPassword: password }),
    });

    const data = await res.json();

    if (res.ok) {
      message.textContent =
        "Contraseña actualizada correctamente. Ahora puedes iniciar sesión.";
    } else {
      error.textContent = data.message || "Error al restablecer la contraseña";
    }
  } catch (e) {
    error.textContent = "Error de conexión con el servidor";
  }
}
