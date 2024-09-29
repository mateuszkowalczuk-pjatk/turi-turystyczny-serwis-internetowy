import Button from "../Button";
import styles from './AuthButtons.module.css'

const AuthButtons = () => {
    return (
        <div className={styles.authButtons}>
            <Button
                text={"Zaloguj się"}
            />
            <Button
                text={"Zarejestruj się"}
            />
        </div>
    )
}

export default AuthButtons;