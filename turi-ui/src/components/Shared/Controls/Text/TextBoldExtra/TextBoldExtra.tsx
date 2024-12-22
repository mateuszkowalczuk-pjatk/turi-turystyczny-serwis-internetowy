import styles from './TextBoldExtra.module.css'

const TextBoldExtra = ({ text }: { text: string }) => {
    return <p className={styles.text}>{text}</p>
}

export default TextBoldExtra
