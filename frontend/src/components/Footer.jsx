import "../styles/footer.css"

const Footer = () => {
  const year = new Date().getFullYear(); // Pega o ano atual automaticamente

  return (
    <footer className="footer">
      <p> <span className="copy">&copy;</span> {year} Alisson Teles Fraga. All rights reserved.</p>
    </footer>
  );
};

export default Footer;