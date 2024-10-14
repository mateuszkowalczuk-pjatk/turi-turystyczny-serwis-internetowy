import Logo from '../../Logo'
import AuthButtons from './AuthButtons'
import styles from './Header.module.css'

const Header = () => {
    return (
        <div className={styles.header}>
            <Logo />
            <AuthButtons />
        </div>
    )
}

export default Header;