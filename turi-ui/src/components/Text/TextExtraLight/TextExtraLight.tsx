import styles from "./TextExtraLight.module.css";

interface Props {
    text: string;
}

const TextExtraLight = ({ text }: Props) => {
    return (
        <p className={styles.text}>
            { text }
        </p>
    )
}

export default TextExtraLight;