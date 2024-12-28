import styles from './TextMediumExtra.module.css'

const TextMediumExtra = ({ text }: { text: string }) => {
    return <p className={styles.text}>{text}</p>
}

export default TextMediumExtra
