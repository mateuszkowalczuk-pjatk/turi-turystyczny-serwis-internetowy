import styles from './AuthTopLink.module.css'

const AuthTopLink = ({ text }: { text: string }) => {
    return (
        <div className={styles.link}>
            { text }
            {/* opcjonalny tekst po srodku jako link */}
        </div>
    )
}

export default AuthTopLink;