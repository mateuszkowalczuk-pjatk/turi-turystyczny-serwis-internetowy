import { ReactNode } from 'react'
import HeaderLogo from '../HeaderLogo'
import styles from './HeaderLayout.module.css'

interface Props {
    links?: ReactNode;
    buttons?: ReactNode;
}

const HeaderLayout = ({ links, buttons }: Props) => {
    return (
        <div className={styles.header}>
            <HeaderLogo />
            { links }
            { buttons }
        </div>
    )
}

export default HeaderLayout