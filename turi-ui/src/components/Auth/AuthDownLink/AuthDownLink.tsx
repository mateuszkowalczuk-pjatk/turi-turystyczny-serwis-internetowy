import styles from './AuthDownLink.module.css'

interface Props {
    firstLink: string;
    secondLink?: string;
}

const AuthDownLink = ({ firstLink, secondLink}: Props) => {
    return (
        <div className={styles.link}>
            { firstLink }
            { secondLink }
            {/* logika czy dwa po bokach, czy jeden po srodku, czy jeden z prawej */}
        </div>
    )
}

export default AuthDownLink;