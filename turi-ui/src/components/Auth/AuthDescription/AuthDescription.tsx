import styles from './AuthDescription.module.css'

const AuthDescription = ({ text }: { text: string }) => {
    return (
        <div className={styles.description}>
            { text }
        </div>
    )
}

export default AuthDescription;