import styles from './TextMediumExtra.module.css'

interface Props {
    text: string
}

const TextMediumExtra = ({ text }: Props) => {
    return <p className={styles.text}>{text}</p>
}

export default TextMediumExtra
