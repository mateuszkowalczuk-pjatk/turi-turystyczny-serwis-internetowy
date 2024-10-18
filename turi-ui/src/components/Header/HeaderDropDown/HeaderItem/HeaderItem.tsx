import TextRegular from "../../../Controls/Text/TextRegular";
import styles from './HeaderItem.module.css'

interface Props {
    onClick: () => void;
    text: string;
}

const HeaderItem = ({ onClick, text }: Props) => {
    return (
        <div
            className={styles.item}
            onClick={onClick}
        >
            <TextRegular
                text={text}
            />
        </div>
    )
}

export default HeaderItem;