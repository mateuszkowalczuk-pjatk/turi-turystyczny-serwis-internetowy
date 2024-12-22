import styles from './TextMedium.module.css'

const TextMedium = ({ text }: { text: string }) => {
    return <p className={styles.text}>{text}</p>
}

export default TextMedium
