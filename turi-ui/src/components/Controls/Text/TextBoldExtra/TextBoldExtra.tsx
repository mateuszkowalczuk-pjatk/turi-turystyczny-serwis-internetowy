import styles from './TextBoldExtra.module.css'

interface Props {
    text: string;
}

const TextBoldExtra = ({ text }: Props) => {
    return (
        <p className={styles.text}>
            { text }
        </p>
    )
}

export default TextBoldExtra