import { RootState } from "../../../store/store.ts";
import { useTranslation } from "react-i18next";
import { GreyButton } from "../../Controls/Button";
import { useSelector } from "react-redux";
import HeaderWrapper from "../HeaderDropDown/HeaderWrapper";
import styles from './HeaderButtons.module.css'

interface Props {
    text: string;
    firstOnClick: () => void;
    secondOnClick?: () => void;
}

const HeaderButtons = ({ text, firstOnClick, secondOnClick }: Props) => {
    const { t } = useTranslation();
    const isAuthenticated = useSelector((state: RootState) => state.auth.isAuthenticated);

    return (
        <div className={styles.buttons}>
            <GreyButton
                text={ text }
                onClick={firstOnClick}
            />
            {!isAuthenticated ? (
                <GreyButton
                    text={t('header.sign-up-button')}
                    onClick={secondOnClick}
                />
            ) : (
                <HeaderWrapper />
            )}
        </div>
    )
}

export default HeaderButtons;