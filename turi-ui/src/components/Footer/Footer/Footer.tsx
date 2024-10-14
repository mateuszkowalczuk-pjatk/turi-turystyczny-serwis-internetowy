import FooterHeader from './FooterHeader'
import FooterContent from './FooterContent'
import FooterCopyright from './FooterCopyright'
import styles from './Footer.module.css'

const Footer = () => {
    return (
        <div className={styles.footer}>
            <FooterHeader />
            <FooterContent />
            <FooterCopyright />
        </div>
    );
}

export default Footer;