import styles from "./TextRegular.module.css";

interface Props {
    text: string;
}

const TextRegular = ({ text }: Props) => {
    return (
        <p className={styles.text}>
            { text }
        </p>
    )
}

export default TextRegular;