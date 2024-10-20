import TextRegular from '../../../Controls/Text/TextRegular'
import styles from './HeaderDropDownItem.module.css'

interface Props {
    onClick: () => void;
    text: string;
}

const HeaderDropDownItem = ({ onClick, text }: Props) => {
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

export default HeaderDropDownItem