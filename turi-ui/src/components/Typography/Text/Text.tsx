import styles from './Text.module.css';

interface Props {
    text: string;
}

const Text = ({ text }: Props) => {
    return (
        <p className={styles.text}>
            { text }
        </p>
    )
}

export default Text;